import json
from conexiones import *
from modulo_correos import *
from socket import *
import time
def login(usuario,password):
	respuesta="False\r \n"
	consulta="call login('"+usuario+"','"+password+"')"
	if consultaSQL(consulta):
		respuesta="True\r \n"
	return respuesta
def recuperar_clave(correo):
	respuesta="False\r \n"
	verificar="call verificacion_correo('"+correo+"')"
	if consultaSQL(verificar):
		respuesta="True\r \n"
		#Si existe el correo en la base de datos
		consulta="call recuperar('"+correo+"')"
		res=consultaRecuperar(consulta) 
		row = res.fetchone()
		enviar_recuperacion(str(row[1]),str(row[2]),str(row[3]),str(row[4]))
	else:
		#el correo no esta registrado en la base de datos
		print "rayos"
	return respuesta
def registro(nombre,usuario,correo,contra):
	respuesta="False\r \n"
	consulta="call Crear_Usuarios('"+nombre+"','"+usuario+"','"+correo+"','"+contra+"')"
	verificar="call verificacion_correo('"+correo+"')"
	verificar2="call verificar_usuario('"+usuario+"')"
	if consultaSQL(verificar):
		print "Ya existe un usuario con ese correo"
		respuesta="Ya existe un usuario con ese correo\r \n"
	else:
		if consultaSQL(verificar2):
			print"Usuario no Disponible"
			respuesta="Usuario no Disponible\r \n"
		else:
			if registroSQL(consulta):
				respuesta="True\r \n"
				enviar_correo(usuario,contra);
	return respuesta
def generar_codigo(id_promocion,id_usuario):
	meses=['E','F','M','A','Y','J','L','G','S','C','N','B']
	dia=time.strftime("%d")
	mes=time.strftime("%m")
	substring=int(dia)+int(mes)
	codigo=str(meses[int(mes)-1])+str(id_usuario)+str(id_promocion)+str(substring)
	return codigo
def obtener_id(usuario):
	consulta="call obtener_id('"+usuario+"')"
	respuesta=consultaId(consulta)
	return respuesta
def cargar(usuario):
	id_usuario=obtener_id(usuario)
	consulta="call promocion()"
	respuesta=consultaPromociones(consulta) 
	row = respuesta.fetchone()
	my_json_string="Cabecera"
	while row is not None:
		codigo=generar_codigo(row[0],id_usuario[0])
		my_json_string =my_json_string+"~"+ json.dumps({'fecha': row[1], 'titulo': row[2], 'contenido': row[3], 'ruta': row[4], 'imagen': row[5], 'empresa': row[6], 'direccion': row[7], 'codigo': codigo, 'imagen2': row[9]})
  		#print(row)
  		row = respuesta.fetchone()
	#print my_json_string+"\r \n"
	return my_json_string
def carga_contenido(usuario,tipo):
	id_usuario=obtener_id(usuario)
	consulta="call promocion_contenido('"+tipo+"')"
	respuesta=consultaPromociones(consulta) 
	row = respuesta.fetchone()
	my_json_string="Cabecera"
	while row is not None:
		codigo=generar_codigo(row[0],id_usuario[0])
		my_json_string =my_json_string+"~"+ json.dumps({'fecha': row[1], 'titulo': row[2], 'contenido': row[3], 'ruta': row[4], 'imagen': row[5], 'empresa': row[6], 'direccion': row[7], 'codigo': codigo, 'imagen2': row[9]})
  		#print(row)
  		row = respuesta.fetchone()
	#print my_json_string+"\r \n"
	return my_json_string
def recibir_datos():
	HOST = "192.168.0.13" #local host
	#HOST = "190.148.250.130" #local host
	PORT = 7000 #open port 7000 for connection
	s = socket(AF_INET, SOCK_STREAM)
	s.bind((HOST, PORT))
	s.listen(5000) #how many connections can it receive at one time
 	#accept the connection
	while True:
		conn, addr = s.accept()
		#print "Connected by: " , addr #print the address of the person connected
		data = conn.recv(1024) #how many bytes of data will the server receive
		#print "Se recibio la info: ", data
		j = json.loads(data)
		if j['accion']=="login":
			respuesta=login(j['usuario'],j['password'])
			#print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="registro":
			respuesta=registro(j['nombre'],j['usuario'],j['correo'],j['contra'])
			#print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="cargar":
			respuesta=cargar(j['usu_sesion'])
			#print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="carga_contenido":
			respuesta=carga_contenido(j['usu_sesion'],j['tipo'])
			#print respuesta
			conn.send(respuesta)
			conn.close()
		elif j['accion']=="recuperar":
			respuesta=recuperar_clave(j['correo'])
			conn.send(respuesta)
			conn.close()
		else:
			#print "accion desconocida"
			conn.send(respuesta)
			conn.close()

recibir_datos()