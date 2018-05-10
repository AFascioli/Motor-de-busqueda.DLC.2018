<%-- 
    Document   : Resultado
    Created on : Apr 24, 2018, 10:14:20 AM
    Author     : dlcusr
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <title>Buscador</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="css/estilo.css">
    </head>
    <body>
        
                <header class="header">
                    Motor de b&uacute;squeda
                </header>
            
            <div class="col-md-12" id="buscador">
                <div id="cuerpobuscador">
                    <form method="post" action="servletconsulta2">
                        <label id="campobusqueda">
                            Ingrese texto de b&uacute;squeda: &nbsp;
                        </label>
                        <input class="form-control" type="text" placeholder="Ingrese su b&uacute;squeda" maxlength="120" id="txt_busqueda" name ="txt_busqueda" autofocus/>
                        <input class="btn btn-md btn-primary" type="submit" value="Buscar" name="buscar" />
                    </form>
                    <form method="post" action="servletindexar">  
                        <label id="cmd_indexar">
                            Presione aqu&iacute; para indexar el nuevo archivo &nbsp;
                        </label>
                        <input class="btn btn-md btn-primary" type="submit" value="Indexar" name="index" />
                    </form>

                </div>
            </div>  

            <div class="col-md-12" id="resultado">
                <c:choose>
                    <c:when test="${resultado==null}">
                        <label>ERROR</label>
                    </c:when>
                    <c:otherwise>
                        <div    class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <th>T&iacute;tulo</th>
                                    <th>Nombre de documento</th>
                                    <th>Peso</th>
                                    <th>Frecuencia de los terminos</th>
                                </tr>

                                <c:forEach items="${resultado}" var="documento" begin="0" end="5" >
                                    <tr>
                                        <td><a href="${documento.nombre}"><c:out value="${documento.titulo}"> </c:out></a></td>
                                        <td><c:out value="${documento.nombre}"> </c:out> </td>
                                        <td><c:out value="${documento.peso}"> </c:out></td>
                                        </tr>
                                </c:forEach>

                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>

            </div>
        
        <footer class="footer">
            Dise&nacute;o de Lenguajes de Consulta, 2018
            Autores: Ponce Diego, Bargiano Florencia y Fascioli Agust&iacute;n
        </footer> 

    </body>
</html>

