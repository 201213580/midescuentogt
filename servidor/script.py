from socket import *
import MySQLdb
import json
def login(usuario,password):
	respuesta="False\r \n"
	consulta="Select 1 from Usuario where Usuario='"+usuario+"' and Password='"+password+"'"
	if consultaSQL(consulta):
		respuesta="True\r \n"
	return respuesta 
def consultaSQL(consulta):
	respuesta=True
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cnx.close()
	return respuesta


HOST = "192.168.0.13" #local host
PORT = 7000 #open port 7000 for connection
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1) #how many connections can it receive at one time
 #accept the connection
while True:
	conn, addr = s.accept()
	#print "Connected by: " , addr #print the address of the person connected
	data = conn.recv(1024) #how many bytes of data will the server receive
	#print "Se recibio la info: ", data
	j = json.loads(data)
	respuesta=login(j['usuario'],j['password'])
	#reply = raw_input("Reply: ") #server's reply to the client
	conn.send(respuesta)
	conn.close()
	break