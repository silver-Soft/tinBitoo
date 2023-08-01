package com.softurasolutions.tinbitoo.Modelos;

import java.lang.reflect.Array;

public class FiltrosModelo {

    private String strBuscar;
    private Integer intEstado;
    private String strMunicipio;
    private Integer kilometros;
    private Float latitud;
    private Float longitud;
    private Array idTipoNegocio;
    private boolean blnEntrega = true;
    private Integer idGiro;
    private Array idCategoriaNegocio;
    private Integer idEstado;
    private Integer idMunicipio;
    private Integer idLocalidad;
    private Integer tipoBusqueda;
    private Integer idNegocio;
    private String abierto;
    private Integer organizacion;
    private boolean limpiarF;
    private Integer id_persona;
    private String user;
    private Integer typeGetOption;
    private String letraInicial;

    public FiltrosModelo(String strBuscar, Integer intEstado, String strMunicipio, Integer kilometros, Float latitud, Float longitud, Array idTipoNegocio, boolean blnEntrega, Integer idGiro, Array idCategoriaNegocio, Integer idEstado, Integer idMunicipio, Integer idLocalidad, Integer tipoBusqueda, Integer idNegocio, String abierto, Integer organizacion, boolean limpiarF, Integer id_persona, String user, Integer typeGetOption, String letraInicial) {
        this.strBuscar = strBuscar;
        this.intEstado = intEstado;
        this.strMunicipio = strMunicipio;
        this.kilometros = kilometros;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idTipoNegocio = idTipoNegocio;
        this.blnEntrega = blnEntrega;
        this.idGiro = idGiro;
        this.idCategoriaNegocio = idCategoriaNegocio;
        this.idEstado = idEstado;
        this.idMunicipio = idMunicipio;
        this.idLocalidad = idLocalidad;
        this.tipoBusqueda = tipoBusqueda;
        this.idNegocio = idNegocio;
        this.abierto = abierto;
        this.organizacion = organizacion;
        this.limpiarF = limpiarF;
        this.id_persona = id_persona;
        this.user = user;
        this.typeGetOption = typeGetOption;
        this.letraInicial = letraInicial;
    }

    public String getStrBuscar() {
        return strBuscar;
    }

    public void setStrBuscar(String strBuscar) {
        this.strBuscar = strBuscar;
    }

    public Integer getIntEstado() {
        return intEstado;
    }

    public void setIntEstado(Integer intEstado) {
        this.intEstado = intEstado;
    }

    public String getStrMunicipio() {
        return strMunicipio;
    }

    public void setStrMunicipio(String strMunicipio) {
        this.strMunicipio = strMunicipio;
    }

    public Integer getKilometros() {
        return kilometros;
    }

    public void setKilometros(Integer kilometros) {
        this.kilometros = kilometros;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Array getIdTipoNegocio() {
        return idTipoNegocio;
    }

    public void setIdTipoNegocio(Array idTipoNegocio) {
        this.idTipoNegocio = idTipoNegocio;
    }

    public boolean isBlnEntrega() {
        return blnEntrega;
    }

    public void setBlnEntrega(boolean blnEntrega) {
        this.blnEntrega = blnEntrega;
    }

    public Integer getIdGiro() {
        return idGiro;
    }

    public void setIdGiro(Integer idGiro) {
        this.idGiro = idGiro;
    }

    public Array getIdCategoriaNegocio() {
        return idCategoriaNegocio;
    }

    public void setIdCategoriaNegocio(Array idCategoriaNegocio) {
        this.idCategoriaNegocio = idCategoriaNegocio;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Integer getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(Integer idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public Integer getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(Integer tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getAbierto() {
        return abierto;
    }

    public void setAbierto(String abierto) {
        this.abierto = abierto;
    }

    public Integer getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Integer organizacion) {
        this.organizacion = organizacion;
    }

    public boolean isLimpiarF() {
        return limpiarF;
    }

    public void setLimpiarF(boolean limpiarF) {
        this.limpiarF = limpiarF;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getTypeGetOption() {
        return typeGetOption;
    }

    public void setTypeGetOption(Integer typeGetOption) {
        this.typeGetOption = typeGetOption;
    }

    public String getLetraInicial() {
        return letraInicial;
    }

    public void setLetraInicial(String letraInicial) {
        this.letraInicial = letraInicial;
    }
}
