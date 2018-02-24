package me.yxy.mydays.tools

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object URLTool{

   fun get(url:String):String?{
       val urlAddress = URL(url)
       val connection = urlAddress.openConnection()
       val inputStream = BufferedReader(InputStreamReader(
               connection.getInputStream()))

       var inputLine: String? = inputStream.readLine()
       var output:String? = ""
       while ( inputLine != null){
           output += inputLine
           inputLine = inputStream.readLine()
       }

       inputStream.close()
       return output
   }
}