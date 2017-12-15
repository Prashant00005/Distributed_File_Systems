# Distributed_File_Systems
I have developed the Distributed File System in Java as <b>Web Application</b> with <b>3-key mutual authentication.</b>
<br>The functionalities that are covered in this project are:
<ul>
<li>Distributed Transparent File Access
<li>Security Service
<li>Directory Service
<li>Caching
<li>Lock Service
</ul>

<br>I have added a Rough Sketch.jsp as a reference to showcase the movement of messages amongst differrent nodes in the distributed network.
<br>There are five nodes in the network:
<ul>
  <li> Client - Sends a request to Authorization Server for a secure login from the web page.This request has username and password. Both of these attributes are encrypted with password attribute for security purpose using Blowfish encryption and encoded with base64.
    <li> Authorization Server - Sends response to Client whether a secure connection is established or not. In case of secure connection, the server generates 2 keys - Key1 and Key2 using blowfish key generator. The server sends a token encrypted with Key2 along with Key 1 to Client.
      <li> Directory Server
        <li> FileSystem Server
          <li> Lock Server
</ul>

I have encrypted 
