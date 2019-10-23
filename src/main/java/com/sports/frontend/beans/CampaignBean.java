package com.sports.frontend.beans;

public class CampaignBean {
	private String campaignId;
	private Integer instanceId;
	private String esquema;
	private String activo;
	private String padreId; //
	private String fecha_inicio;
	private String fecha_fin;
	private String fecha_expiracion;
	private String mensaje;
	private String subject;
	private String callback;
	private String prioridad; //
	private String status;
	private String paisId;
	private String anchobanda;
	private String destinatarios; //
	private boolean numberOfSubsChecked;
	private String externalName;
	private String idReporte;

	
	public String getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(String idReporte) {
		this.idReporte = idReporte;
	}

	public String getExternalName() {
		String extName = externalName==null||externalName.equals("null")?"":externalName;
		return extName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}
	
	public CampaignBean(){
		numberOfSubsChecked=false;
	}
	
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getAnchobanda() {
		return anchobanda;
	}
	public void setAnchobanda(String anchobanda) {
		this.anchobanda = anchobanda;
	}
	public String getEsquema() {
		return esquema;
	}
	public String getMensaje() {
		return mensaje;
	}
	public String getSubject() {
		return subject;
	}
	public String getCallback() {
		return callback;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public String getFecha_fin() {
		return fecha_fin;
	}
	public String getFecha_expiracion() {
		return fecha_expiracion;
	}
	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public void setFecha_expiracion(String fecha_expiracion) {
		this.fecha_expiracion = fecha_expiracion;
	}
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getPaisId() {
		return paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getPadreId() {
		return padreId;
	}
	public void setPadreId(String padreId) {
		this.padreId = padreId;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getStatus() {
		return status;
	}
	public boolean isNumberOfSubsChecked() {
		return numberOfSubsChecked;
	}
	public void setNumberOfSubsChecked(boolean numberOfSubsChecked) {
		this.numberOfSubsChecked = numberOfSubsChecked;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
