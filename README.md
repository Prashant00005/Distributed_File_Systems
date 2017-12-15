# Distributed_File_Systems
<>Name: Prashant Aggarwal
<br>Id: 17317559

I have developed the Distributed File System in Java as <b>Web Application</b> with <b>3-key mutual authentication.</b>
<br>The functionalities that are covered in this project are:
<ul>
<li>Distributed Transparent File Access
<li>Security Service
<li>Directory Service
<li>Caching
<li>Lock Service
</ul>

<br>I have added a Rough Sketch.jpg as a reference to showcase the movement of messages amongst differrent nodes in the distributed network.
<br>There are five nodes in the network:
<ul>
  <li><b>Client</b> - Sends a request to Authorization Server for a secure login from the web page.This request has username and password. Both of these attributes are encrypted with password attribute for security purpose using Blowfish encryption and encoded with base64.
<br>Sends a request to Directory Server to get the location of the file. This request has filename and username encrypted with key1 along with the token encrypted with key2 that it got from Authorization Server.
<br>Sends a request to File System Server from the webpage to read(open) file whose location is received as response from Directory Server. This request has filename encrypted with key1 and token encrypted with key2.
<br>Sends a write request to File System Server. But, in order to write a file it first needs to put a lock on this file. So a request to Lock server is made. The request consists of token encrypted with key 2 (received at the time of login), filename encrypted with key1 and username (to keep track of who locked the file, and will be released only by that client) encrypted with key1. If the lock servere responds with a success message, client send request to write this file.
<br><br><li><b> Authorization Server</b> - Receives request from Client. Compares the username and password from the table store in MySql database. Sends response to Client whether a secure connection is established or not. In case of successfull secure connection, the server generates 2 keys - Key1 and Key2 using blowfish key generator. The server sends a token encrypted with Key2 along with Key 1 to Client. The token comprises of TTL of 5 minutes, username and key1.
      <br><br><li><b> Directory Server</b> - Receives the request from client. First checks from the Authorization server that the token is valid or not. If it is valid, gets location of file from its lookup table stored in MySql database. Sends location encrypted with Key1 back to the client.
       <br><br><li><b> FileSystem Server</b> - Caters two requests from the client. One is read request and the other is the write request. 
  <br>When a read request is received, first checks from the Authorization Server that the token is valid or not. If token is valid, fetch the file from the server's disk and send it as a response to the client <b>(upload/download model)</b>.For the first time, if a file is not created it will send a message asking the cient to write the file first.
  <br>When a write request is received, first checks from the Authorization Server that the token is still valid or not. If yes, fetch the file from disk and send it in write mode.
         <br><br><li> <b>Lock Server </b>- When a lock request is received for a file, first checks from Authorization Server whether token is valid or not. If valid puts lock on the file in the database and sends a success response back to the client.
  <br>When a lock release request is received, check the validity of token from Authorization Server and release the file and send a success message back to the client.
</ul>
