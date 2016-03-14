kill -9 $(lsof -i:8077 | awk '{print $2}' | tail -n 4)
uwsgi -x uwsgi_conf.xml
nginx -s reload
