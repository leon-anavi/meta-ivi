DESCRIPTION = "GENIVI P1 Abstract components (AC)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${IVI_COREBASE}/meta-ivi/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
# Avoid hardcoding the full layer path into the checksums
LIC_FILES_CHKSUM[vardepsexclude] += "IVI_COREBASE"

inherit packagegroup

PACKAGES = "\
    packagegroup-abstract-component-p1 \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    bluez5 \
    bluez5-obex \
    libbluetooth-plugins-service \
    glibc \
    ofono \
    wayland-ivi-extension \
    weston weston-examples \
    "
