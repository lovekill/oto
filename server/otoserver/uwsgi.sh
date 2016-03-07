#uwsgi -s 127.0.0.1:9090 -w wsgi --enable-threads&
uwsgi --http :9090 --module otoserver.wsgi
