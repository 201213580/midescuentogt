#!/usr/bin/env python
# -*- coding: utf-8 -*-
from email.mime.text import MIMEText
from smtplib import SMTP


def enviar_recuperacion(nombre,usuario,correo,contra):
    from_address = "soportemicupongt@gmail.com"
    to_address = correo
    message = """<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                    <head> 
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                        <title>Registro MiCuponGT</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    </head>
                    <body style="margin: 0; padding: 0;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" bgcolor="#ffffff" style="padding: 10px 0 10px 0;">
                                    <img src="http://www.futuraradio.com.gt/recursos/logo.png" alt="Creating Email Magic" width="150" height="150" style="display: block;" />
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ffffff" style="padding: 40px 30px 40px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 24px;" align="center">
                                                <b>RECUPERACION</b><br/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;">
                                                Hola<br/><br/>
                                                Haz solicitado tu informacion de usuario. Gracias por utilizar MiCuponGT.
                                                <br/><br/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                        <td style="color: #153643; font-family: Arial, sans-serif; font-size: 20px;">
                                                            <b>Informacion de Cuenta</b><br/><br/>
                                                        </td>
                                                    </tr>   
                                                    <tr>
                                                        <td width="260" valign="top" style="color: #153643; font-family: Arial, sans-serif; font-size: 20px; line-height: 20px;">
                                                            NOMBRE:<br/><br/> """+nombre+"""<br/><br/>
                                                            USUARIO:<br/><br/> """+usuario+"""

                                                        </td>
                                                        <td width="260" valign="top" style="color: #153643; font-family: Arial, sans-serif; font-size: 20px; line-height: 20px;">
                                                            CORREO:<br/> <br/>"""+correo+""" <br/><br/>
                                                            CLAVE:<br/> <br/>"""+contra+"""<br/><br/><br/>                                                            
                                                        </td>
                                                    </tr>
                                                    <br/>
                                                    
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 24px;" align="center">
                                                <b><b>Si no haz solicitado tus datos de usuario por favor comunicate con nosotros soportemicupongt@gmail.com y con gusto te ayudaremos.</b><br/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ee4c50" style="padding: 30px 30px 30px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;">
                                                MiCuponGt 2017<br/>
                                                Tel: 59791821-56989402
                                                <br/> 
                                                Correo: soportemicupongt@gmail.com
                                            </td>
                                            <td>
                                                <td align="right">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.facebook.com/">
                                                                    <img src="http://www.futuraradio.com.gt/recursos/facebook.png" alt="Twitter" width="38" height="38" style="display: block;" border="0" />
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </body>
                </html>"""
    mime_message = MIMEText(message, "html", _charset="utf-8")
    mime_message["From"] = from_address
    mime_message["To"] = to_address
    mime_message["Subject"] = "RECUPERAR DATOS"
    smtp = SMTP("smtp.gmail.com:25")
    smtp.starttls()
    smtp.login(from_address, "Manuel5897-")
    smtp.sendmail(from_address, to_address, mime_message.as_string())
    smtp.quit()
def enviar_correo(tipo,usuario,contra):
    from_address = "soportemicupongt@gmail.com"
    to_address = usuario
    message2 = """<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                    <head> 
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                        <title>Registro MiCuponGT</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    </head>
                    <body style="margin: 0; padding: 0;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" bgcolor="#ffffff" style="padding: 10px 0 10px 0;">
                                    <img src="http://www.futuraradio.com.gt/recursos/logo.png" alt="Creating Email Magic" width="150" height="150" style="display: block;" />
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ffffff" style="padding: 40px 30px 40px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 24px;" align="center">
                                                <b>REGISTRO EXITOSO</b><br/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;">
                                                Bienvenido!<br/>
                                                Gracias por unirte a MiCuponGt, ya estas listo para disfrutar de las mejores ofertas, promociones, descuentos y muchas cosas mas.
                                                <br/><br/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                        <td style="color: #153643; font-family: Arial, sans-serif; font-size: 20px;">
                                                            <b>Informacion de Registro por medio de GMAIL</b><br/>
                                                        </td>
                                                    </tr>   
                                                    <tr>
                                                        <td width="260" valign="top" style="color: #153643; font-family: Arial, sans-serif; font-size: 20px; line-height: 20px;">
                                                            Correo: """+usuario+"""
                                                        </td>
                                                        <td style="font-size: 0; line-height: 0;" width="20">
                                                            &nbsp;
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ee4c50" style="padding: 30px 30px 30px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;">
                                                MiCuponGt 2017<br/>
                                                Tel: 59791821-56989402
                                                <br/> 
                                                Correo: soportemicupongt@gmail.com
                                            </td>
                                            <td>
                                                <td align="right">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.facebook.com/">
                                                                    <img src="http://www.futuraradio.com.gt/recursos/facebook.png" alt="Twitter" width="38" height="38" style="display: block;" border="0" />
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </body>
                </html>"""
    message = """<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                    <head> 
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                        <title>Registro MiCuponGT</title>
                        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    </head>
                    <body style="margin: 0; padding: 0;">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" bgcolor="#ffffff" style="padding: 10px 0 10px 0;">
                                    <img src="http://www.futuraradio.com.gt/recursos/logo.png" alt="Creating Email Magic" width="150" height="150" style="display: block;" />
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ffffff" style="padding: 40px 30px 40px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 24px;" align="center">
                                                <b>REGISTRO EXITOSO</b><br/>
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td style="color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;">
                                                Bienvenido!<br/>
                                                Gracias por unirte a MiCuponGt, ya estas listo para disfrutar de las mejores ofertas, promociones, descuentos y muchas cosas mas.
                                                <br/><br/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                                    <tr>
                                                        <td style="color: #153643; font-family: Arial, sans-serif; font-size: 20px;">
                                                            <b>Informacion de Registro</b><br/>
                                                        </td>
                                                    </tr>   
                                                    <tr>
                                                        <td width="260" valign="top" style="color: #153643; font-family: Arial, sans-serif; font-size: 20px; line-height: 20px;">
                                                            USER: """+usuario+"""
                                                        </td>
                                                        <td style="font-size: 0; line-height: 0;" width="20">
                                                            &nbsp;
                                                        </td>
                                                        <td width="260" valign="top" style="color: #153643; font-family: Arial, sans-serif; font-size: 20px; line-height: 20px;">
                                                            PASSWORD: """+contra+"""
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td bgcolor="#ee4c50" style="padding: 30px 30px 30px 30px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td style="color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;">
                                                MiCuponGt 2017<br/>
                                                Tel: 59791821-56989402
                                                <br/> 
                                                Correo: soportemicupongt@gmail.com
                                            </td>
                                            <td>
                                                <td align="right">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>
                                                                <a href="http://www.facebook.com/">
                                                                    <img src="http://www.futuraradio.com.gt/recursos/facebook.png" alt="Twitter" width="38" height="38" style="display: block;" border="0" />
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </body>
                </html>"""
    if(tipo=='tradicional'):
        mime_message = MIMEText(message, "html", _charset="utf-8")    
    else:
        mime_message = MIMEText(message2, "html", _charset="utf-8")

    mime_message["From"] = from_address
    mime_message["To"] = to_address
    mime_message["Subject"] = "Registro MiCuponGt"
    smtp = SMTP("smtp.gmail.com:25")
    smtp.starttls()
    smtp.login(from_address, "Manuel5897-")
    smtp.sendmail(from_address, to_address, mime_message.as_string())
    smtp.quit()
