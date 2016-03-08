DESCRIPTION = "SOTA Reference Implementation project - Client"
HOMEPAGE = "https://github.com/advancedtelematic/rvi_sota_client"
LICENSE = "MPL-2.0"

inherit cargo systemd

SRC_URI = "git://github.com/advancedtelematic/rvi_sota_client.git;protocol=https"
SRC_URI += "file://rvi-sota-client.service"
SRC_URI += "file://run-fix-path.patch"
SRCREV="57e803803691acab8e443f1631767edaec9da10f"
LIC_FILES_CHKSUM="file://LICENSE;md5=65d26fcc2f35ea6a181ac777e42db1ea"

S = "${WORKDIR}/git"

BBCLASSEXTEND = "native"

DEPENDS += " dbus"
RDEPENDS_${PN} += " dbus-lib libcrypto libssl"

SYSTEMD_SERVICE_${PN} = "rvi-sota-client.service"

do_configure_append() {
 # Use patched dbus-rs version for ARM
 if ${@bb.utils.contains('TUNE_FEATURES', 'arm', 'true', 'false', d)}; then
  rm -f ${WORKDIR}/.cargo/config
  mkdir -p ${WORKDIR}/../.cargo
  echo "paths = [\"${BASE_WORKDIR}/${BUILD_SYS}/cargo-native/0.7.0-r0/dbus-rs\"]" > ${WORKDIR}/../.cargo/config
 fi
}

do_install_append() {
 install -m 0755 -p -D ${S}/docker/client.toml ${D}/var/sota/client.toml
 install -m 0755 -p -D ${S}/docker/run.sh ${D}${prefix}/bin/run.sh
 if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
  install -p -D ${WORKDIR}/rvi-sota-client.service ${D}${systemd_unitdir}/system/rvi-sota-client.service
 fi
}

FILES_${PN} += "/var/sota/"
FILES_${PN} += "/var/sota/client.toml"
FILES_${PN} += "${prefix}/bin/"
FILES_${PN} += "${prefix}/bin/run.sh"
