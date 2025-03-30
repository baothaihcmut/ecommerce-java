{{- define "common.secret" -}}
apiVersion: v1
kind: Secret
metadata:
  name: {{ .Release.Name }}-{{ .Chart.Name }}-{{ .Values.secret.name }}-secret
  labels:
    {{- include "common.labels" . | nindent 4 }}
type: {{- .Values.secret.type | default "Opaque"}}
data:
    {{- toYaml .Values.secret.data | nindent 4 }}
{{- end }}