# acl <acl_name> <type> <data> ... [FORMAT of acl]
acl localnet src 10.0.0.0/16     # RFC1918 possible internal network
acl my_whitelist dstdomain "/etc/squid/whitelist"
# acl my_whitelist_regex url_regex "/etc/squid/whitelist_regex"

acl SSL_ports port 443
acl Safe_ports port 80          # http
acl Safe_ports port 443         # https
acl Safe_ports port 21          # ftp
acl Safe_ports port 70          # gopher
acl Safe_ports port 210         # wais
acl Safe_ports port 1025-65535  # unregistered ports
acl Safe_ports port 280         # http-mgmt
acl Safe_ports port 488         # gss-http
acl Safe_ports port 591         # filemaker
acl Safe_ports port 777         # multiling http
acl CONNECT method CONNECT      # HTTP CONNECT method

#
# Recommended minimum Access Permission configuration:
#
# Deny requests to certain unsafe ports
http_access deny !Safe_ports

# Deny CONNECT to other than secure SSL ports
http_access deny CONNECT !SSL_ports

# Only allow cachemgr access from localhost
http_access allow localhost manager
http_access deny manager

# We strongly recommend the following be uncommented to protect innocent
# web applications running on the proxy server who think the only
# one who can access services on "localhost" is a local user
http_access deny to_localhost
# above line blocks attempts to request http:///localhost/... on the proxy server via the proxy

#
# INSERT YOUR OWN RULE(S) HERE TO ALLOW ACCESS FROM YOUR CLIENTS

# If localnet is allowed as below, any access from localnet is allowd, and whitelist is not functioning
# http_access allow localnet
http_access allow localhost

http_access allow my_whitelist
# http_access allow my_whitelist_regex
# And finally deny all other access to this proxy
http_access deny all

# Squid normally listens to port 3128
# http_port 3128
http_port 8080

# Uncomment and adjust the following to add a disk cache directory.
#cache_dir ufs /var/spool/squid 100 16 256

# Leave coredumps in the first cache dir
coredump_dir /var/spool/squid

#
# Add any of your own refresh_pattern entries above these.
#
refresh_pattern ^ftp:           1440    20%     10080
refresh_pattern ^gopher:        1440    0%      1440
refresh_pattern -i (/cgi-bin/|\?) 0     0%      0
refresh_pattern .               0       20%     4320

################## No Default #################
# If squid crashes, send crash report to this e-mail address
cache_mgr shivaq777@gmail.com

# show specific hostname
# visible_hostname <your desired hostname>
visible_hostname in_my_head

################### HOW TO HIDE PROXY Serer ########################
# hide ip address of the client from HTTP requests.
# so, "X-Forwarded-For:192.168.0.1" is hidden
# forwarded_for off

# You can hide your info, but official said,
# WARRRRNING: Doing this VIOLATES the HTTP standard.  Enabling this feature could make you liable for problems which it causes.
# request_header_access Referer deny all
# request_header_access X-Forwarded-For deny all
# request_header_access Via deny all
