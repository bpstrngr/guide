import 'dart:io';
import 'dart:convert';
import 'dart:async';
import 'package:args/args.dart';

Future main() async
{var server = await HttpServer.bind(InternetAddress.loopbackIPv4,4444,);
 print('Listening on localhost:${server.port}');
 await for(var request in server){handleRequest(request);}
 //await for (HttpRequest request in server){request.response ..write('Hello, world!') ..close();}
}

void handleRequest(HttpRequest request)
{try
{if (request.method == 'GET') {handleGet(request);}
 else{request.response ..statusCode = HttpStatus.methodNotAllowed ..write('Unsupported request: ${request.method}.')..close();}
}catch (e) {print('Exception in handleRequest: $e');}
 print('Request handled.');
}

void handleGet(HttpRequest request)
{final guess = request.uri.queryParameters['q'];
 final response = request.response;
 response.statusCode = HttpStatus.ok;
 if(guess == "hi") 
{response ..writeln('true') ..writeln("hi") ..close();
 print("someone arrived");
}else{response ..writeln('who are you?') ..close();}
}


const line = 'line-number';
ArgResults results;

void main2(List<String> arguments)
{print('hello, my name is '+arguments[0]);
 exitCode = 0; //presume success
 final parser = new ArgParser()
      ..addFlag(line, negatable: false, abbr: 'n');
 results = parser.parse(arguments);
 List<String> paths = results.rest;
 dcat(paths, results[line]);
}

Future dcat(List<String> paths, bool showLineNumbers) async {if (paths.isEmpty) {stdin.pipe(stdout);} // No files provided as arguments. Read from stdin and print each line.

else 
{for (var path in paths)
{int line = 1;
 Stream lines = new File(path).openRead().transform(utf8.decoder).transform(const LineSplitter());
 try 
{await for(var line in lines)
{if (showLineNumbers) {stdout.write('${line++} ');}
 stdout.writeln(line);
}
}catch (_){_handleError(path);}
}
}
}

Future _handleError(String path) async 
{if (await FileSystemEntity.isDirectory(path)){stderr.writeln('error: $path is a directory');}
 else {exitCode = 2;}
}
