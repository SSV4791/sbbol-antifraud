oc delete route --all
oc delete deployment --all
oc delete svc --all
oc delete dr --all
oc delete pod --all --force=true --grace-period=0
oc delete vs --all
oc delete configmap --all
oc delete se --all
oc delete gw --all

oc delete secret istio-ingressgateway-ca-certs
oc delete secret istio-ingressgateway-certs
oc delete secret istio-egressgateway-ca-certs
oc delete secret istio-egressgateway-certs
oc delete secret pgdb-main-secret
oc delete secret pgdb-si-secret
oc delete secret secret-appjournalstubsettings
oc delete secret ott-secret
oc delete secret ott-ingress-passwords
#oc delete sa dataspace-core