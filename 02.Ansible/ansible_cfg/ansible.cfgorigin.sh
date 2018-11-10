# config file for ansible -- http://ansible.com/
# $HOME/.ansible.cfg
# ==============================================

[defaults]

############# Basic settings #############
# This points to the file that lists your hosts
inventory       = inventory/

# additional paths to search for roles in, colon separated
# roles_path    = ./roles:/etc/ansible/roles
roles_path    = ./roles

# if no hosts is specified, the default behaviour is apply to all hosts. [pattern=*] disable that behaviour
pattern        = *
# the default number of parallel processes to spawn when communicating with remote hosts.
forks          = 25
# Default language to communicate between the module and the system.
# this settings will be deprecated in ver.2.9
# module_lang    = en_US.UTF-8

# format of string {{ ansible_managed }} available within Jinja2
# templates indicates to users editing templates files will be replaced.
# replacing {file}, {host} and {uid} and strftime codes with proper values.
ansible_managed = Ansible managed: {file} modified on %Y-%m-%d %H:%M:%S by {uid} on {host}

# logging is off by default unless this path is defined
# if so defined, consider logrotate
#log_path = /var/log/ansible/main.log

########### remote settings ###########
# put scripts on remote host temporary
remote_tmp     = $HOME/.ansible/tmp

# default polling interval for asynchronous tasks
poll_interval  = 15

#  the default username ansible will connect as for /usr/bin/ansible-playbook
# (/usr/bin/ansible will use current user as default)
#remote_user = root

# if set, always use this private key file for authentication, same as
# if passing --private-key to ansible or ansible-playbook
private_key_file = ~/.ssh/MyKey.pem

################## gather facts ##################
#caching config
gathering = smart
# cache facts with this type
fact_caching = jsonfile
# where to cache facts
fact_caching_connection = ./logs/factcache
fact_caching_timeout = 86400

######################### connections ########################
# how to connect to remote node.smart is select it automatically
transport      = smart
#remote_port    = 22
# uncomment this to disable SSH key host checking
host_key_checking = False
# what flags to pass to sudo
#sudo_flags = -H
# SSH timeout
timeout = 10

################## modules ##################
# This is the default module name (-m) value for /usr/bin/ansible. The default is the ‘command’ module. Remember the command module doesn’t support shell variables, pipes, or quotes, so you might wish to change it to ‘shell’:
#module_name = command

# use this shell for commands executed under sudo
# you may need to change this to bin/bash in rare instances
# if sudo is constrained
#executable = /bin/sh

#  allows enabling additional Jinja2 extensions:
#jinja2_extensions = jinja2.ext.do,jinja2.ext.i18n

# by default (as of 1.3), Ansible will raise errors when attempting to dereference
# Jinja2 variables that are not set in templates or action lines. Uncomment this line
# to revert the behavior to pre-1.3.
#error_on_undefined_vars = False

# by default (as of 1.6), Ansible may display warnings based on the configuration of the
# system running ansible itself. This may include warnings about 3rd party packages or
# other conditions that should be resolved if possible.
# to disable these warnings, set the following value to False:
#system_warnings = True

# by default (as of 1.4), Ansible may display deprecation warnings for language
# features that should no longer be used and will be removed in future versions.
# to disable these warnings, set the following value to False:
#deprecation_warnings = True

# (as of 1.8), Ansible can optionally warn when usage of the shell and
# command module appear to be simplified by using a default Ansible module
# instead.  These warnings can be silenced by adjusting the following
# setting or adding warn=yes or warn=no to the end of the command line
# parameter string.  This will for example suggest using the git module
# instead of shelling out to the git command.
# command_warnings = False
# set plugin path directories here, separate with colons
action_plugins     = /usr/share/ansible_plugins/action_plugins
callback_plugins   = /usr/share/ansible_plugins/callback_plugins
connection_plugins = /usr/share/ansible_plugins/connection_plugins
lookup_plugins     = /usr/share/ansible_plugins/lookup_plugins
vars_plugins       = /usr/share/ansible_plugins/vars_plugins
filter_plugins     = ./filter_plugins

# by default callbacks are not loaded for /bin/ansible, enable this if you
# want, for example, a notification or logging callback to also apply to
# /bin/ansible runs
#bin_ansible_callbacks = False

# retry files
retry_files_enabled = False
# retry_files_save_path = ~/.ansible-retry
################## visual ##################
# don't like cows?  that's unfortunate.
# set to 1 if you don't want cowsay support or export ANSIBLE_NOCOWS=1
nocows = 1

# don't like colors either?
# set to 1 if you don't want colors, or export ANSIBLE_NOCOLOR=1
# nocolor = 1

################## security? ##################
# the CA certificate path used for validating SSL certs. This path
# should exist on the controlling node, not the target nodes
# common locations:
# RHEL/CentOS: /etc/pki/tls/certs/ca-bundle.crt
# Fedora     : /etc/pki/ca-trust/extracted/pem/tls-ca-bundle.pem
# Ubuntu     : /usr/share/ca-certificates/cacert.org/cacert.org.crt
#ca_file_path =

# the http user-agent string to use when fetching urls. Some web server
# operators block the default urllib user agent as it is frequently used
# by malicious attacks/scripts, so we set it to something unique to
# avoid issues.
#http_user_agent = ansible-agent

# Auto accept new hostkeys
#host_key_checking = False

[privilege_escalation]
#become=True
become_method='sudo'
become_user='root'
#become_ask_pass=False

[paramiko_connection]

# uncomment this line to cause the paramiko connection plugin to not record new host
# keys encountered.  Increases performance on new host additions.  Setting works independently of the
# host key checking setting above.
#record_host_keys=False


[ssh_connection]

# ssh arguments to use
# Leaving off ControlPersist will result in poor performance, so use
# paramiko on older platforms rather than removing it
#ssh_args = -o ControlMaster=auto -o ControlPersist=60s

# The path to use for the ControlPath sockets. This defaults to
# "%(directory)s/ansible-ssh-%%h-%%p-%%r", however on some systems with
# very long hostnames or very long path names (caused by long user names or
# deeply nested home directories) this can exceed the character limit on
# file socket names (108 characters for most platforms). In that case, you
# may wish to shorten the string below.
#
# Example:
# control_path = %(directory)s/%%h-%%r
#control_path = %(directory)s/ansible-ssh-%%h-%%p-%%r

# Enabling pipelining reduces the number of SSH operations required to
# execute a module on the remote server. This can result in a significant
# performance improvement when enabled, however when using "sudo:" you must
# first disable 'requiretty' in /etc/sudoers

# By default, this option is disabled to preserve compatibility with
# sudoers configurations that have requiretty (the default on many distros).
#
#pipelining = False

# if True, make ansible use scp if the connection type is ssh
# (default is sftp)
#scp_if_ssh = True

[accelerate]
accelerate_port = 5099
accelerate_timeout = 30
accelerate_connect_timeout = 5.0

# The daemon timeout is measured in minutes. This time is measured
# from the last activity to the accelerate daemon.
accelerate_daemon_timeout = 30

# If set to yes, accelerate_multi_key will allow multiple
# private keys to be uploaded to it, though each user must
# have access to the system via SSH to add a new key. The default
# is "no".
#accelerate_multi_key = yes

[selinux]
# file systems that require special treatment when dealing with security context
# the default behaviour that copies the existing context or uses the user default
# needs to be changed to use the file system dependant context.
#special_context_filesystems=nfs,vboxsf,fuse
