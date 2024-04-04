import requests._

// Default settings     
val proxy = ("localhost", 8080)

def sendUrlsToBurp(baseUrl:String): Unit ={
 cpg.identifier.
     codeExact("$_GET").
     inCall.foreach{call=>

       // file name check, "no File" if empty
       def fileName = call.file.name.
                       headOption.
                       getOrElse("no File")
 

       // parameter check, "no Param" if empty
       def literals = call.argument.
                           isLiteral.code.
                           headOption.
                           getOrElse("no Param")

 
       // parsing param name, adding * as value
       val parameter = s"${literals.replace("\"","")}=*"
 
       // final url
       val url = s"$baseUrl/$fileName?$parameter"

       try{
         requests.get(url,proxy = proxy,verifySslCerts = false)
       } catch {
         case e:Exception => println(s"Problem sending request: $url")
       }
 }

}

@main def main(inputPath: String, domain:String) = {
  // ! deleting all projects in the workspace
  workspace.reset
  importCode(inputPath)
  sendUrlsToBurp(domain)

}
