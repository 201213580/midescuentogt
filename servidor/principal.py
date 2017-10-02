import json
from conexiones import *
from socket import *
def login(usuario,password):
	respuesta="False\r \n"
	consulta="Select 1 from Usuario where Usuario='"+usuario+"' and Contra='"+password+"'"
	if consultaSQL(consulta):
		respuesta="True\r \n"
	return respuesta

def recibir_datos():
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
		if j['accion']=='login'
			respuesta=login(j['usuario'],j['password'])
			#reply = raw_input("Reply: ") #server's reply to the client
			conn.send(respuesta)
			conn.close()
			break
		else:
			print "accion desconocida"
			conn.send(respuesta)
			conn.close()
recibir_datos()