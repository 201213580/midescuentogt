import json
from conexiones import *
from socket import *

def login(usuario,password):
	respuesta="False\r \n"
	consulta="Select 1 from usuario where Usuario='"+usuario+"' and Contra='"+password+"'"
	if consultaSQL(consulta):
		respuesta="True\r \n"
	return respuesta

def registro(nombre,usuario,correo,contra):
	respuesta="False\r \n"
	consulta="insert into usuario(Nombre,Usuario,Correo,Contra) values('"+nombre+"','"+usuario+"','"+correo+"','"+contra+"')"
	verificar="Select 1 from usuario where Correo='"+correo+"'"
	verificar2="Select 1 from usuario where Usuario='"+usuario+"'"
	if consultaSQL(verificar):
		print "Ya existe un usuario con ese correo"
		respuesta="Ya existe un usuario con ese correo\r \n"
	else:
		if consultaSQL(verificar2):
			print "Usuario no Disponible"
			respuesta="Usuario no Disponible\r \n"
		else:
			if registroSQL(consulta):
				respuesta="True\r \n"
	return respuesta
def cargar():
	consulta="select * from promocion"
	respuesta=consultaPromociones(consulta) 
	row = respuesta.fetchone()
	my_json_string="Cabecera"
	while row is not None:
		my_json_string =my_json_string+"-"+ json.dumps({'fecha': row[1], 'titulo': row[2], 'contenido': row[3], 'ruta': row[4], 'imagen': row[5]})
  		#print(row)
  		row = respuesta.fetchone()
	#print my_json_string+"\r \n"
	return my_json_string
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
		if j['accion']=="login":
			respuesta=login(j['usuario'],j['password'])
			print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="registro":
			respuesta=registro(j['nombre'],j['usuario'],j['correo'],j['contra'])
			print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="cargar":
			respuesta=cargar()
			print respuesta
			conn.send(respuesta)
			conn.close()
		else:
			print "accion desconocida"
			conn.send(respuesta)
			conn.close()

recibir_datos()