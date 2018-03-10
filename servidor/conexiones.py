import MySQLdb
def consultaRecuperar(consulta):
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	cursor.execute(consulta)
	#cnx.close() no se cierra porque activa una excepcion.
	return cursor
def consultaSQL(consulta):
	respuesta=False
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	cursor.execute(consulta)
	respuesta=cursor.fetchone()
	if respuesta=='1':
		respuesta=True
		cnx.close()
	return respuesta
def consultaId(consulta):
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	cursor.execute(consulta)
	respuesta=cursor.fetchone()
	#cnx.close()
	return respuesta
def consultaPromociones(consulta):
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	try:
		cursor.execute(consulta)
		#cnx.close() no se cierra porque activa una excepcion.
	except Exception, e:
		print '  '
	return cursor
def registroSQL(consulta):
	respuesta=False
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	try:
		cursor.execute(consulta)
		cnx.commit()
		cnx.close()
		respuesta=True
	except Exception, e:
		print 'No se logro realizar la accion'
	return respuesta
def consultaCodigo(consulta):
	respuesta=False
	cnx = MySQLdb.connect(host='localhost', user='root', passwd='',
		  db='micupongt')
	cursor = cnx.cursor()
	try:
		cursor.execute(consulta)
		cnx.commit()
		respuesta=cursor.fetchone()
		#cnx.close() no se cierra porque activa una excepcion.
	except Exception, e:
		print '  '
	return respuesta