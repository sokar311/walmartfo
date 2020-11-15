<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><c:set var="ctx" value="${pageContext['request'].contextPath}"/>

<body id="headerAngular" ng-controller="HeaderController" >

	<header>
		<section class="cabecera-principal">
			<div class="container">
				<div class="row">
					<div class="col-sm-12">
						<div class="cabecera-principal__contenedor">
							
							<div class="contacto">
								<a href="tel:+56974722749">
									<img class="contacto__icono" src="${ctx}/images/iconos/icono-contacto.png" alt="icono contacto">
									<span class="contacto__info">Call center <strong>+56 974722749</strong></span>
								</a>
							</div>

							<div class="logo">
								<a href="${ctx}/home">
									<img src="${ctx}/images/walmartlogo.png" alt="Logo Walmart">
								</a>
							</div>

							<div class="acciones">
								<div class="buscador">
									<img class="buscador__icono" data-toggle="modal" data-target="#modal-buscar" src="${ctx}/images/iconos/icono-lupa.png" alt="icono buscar">
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		</section>
	</header>

	<div class="modal fade buscador-principal" id="modal-buscar" tabindex="-1" role="dialog">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				
				<div class="modal-body">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>

					
					<form class="buscador-principal__formulario">
						<input class="buscador-principal__entrada" ng-model="parametroBusqueda" type="search" placeholder="¡We have thousands of products for sale! 😉">
						<button class="buscador-principal__accion" ng-click="buscarParametro(parametroBusqueda)">
							¡Search!
						</button>
					</form>

					<div ng-cloak >
						<div class="row">
							<div class="col-sm-3">
								<div class="buscador-principal__seccion">
									<ul ng-repeat="palabra in palabras">
										<li><a href="${ctx}/search?parametro={{palabra}}">{{palabra}}</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

