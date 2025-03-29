{{- define "common.gke-cert" -}}
apiVersion: networking.gke.io/v1
kind: ManagedCertificate
metadata:
  name: {{ .Values.gkeCerts.name }}
  labels:
    {{- include "common.labels" . | nindent 4 }}
spec:
  domains:
    {{range .Values.gkeCerts.domains }}
    - {{ . }}
    {{ end }}
{{- end }}