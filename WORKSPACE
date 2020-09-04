workspace(
    name = "libepn",
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    sha256 = "d85951a92c0908c80bd8551002d66cb23c3434409c814179c0ff026b53544dab",
    strip_prefix = "rules_jvm_external-3.3",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/3.3.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "org.apache.commons:commons-math3:3.6.1",
        "junit:junit:4.13",
        "org.ejml:ejml-simple:0.39",
        "org.ejml:ejml-all:0.39",
        "edu.wpi.first.wpilibj:wpilibj-java:2020.3.2",
    ],
    fetch_sources = True,
    repositories = [
        "https://ultralight.retrylife.ca:/maven",
        "https://jcenter.bintray.com/",
        "https://frcmaven.wpi.edu/artifactory/release/",
    ],
)

http_archive(
    name = "com_grail_bazel_compdb",
    strip_prefix = "bazel-compilation-database-master",
    urls = ["https://github.com/grailbio/bazel-compilation-database/archive/master.tar.gz"],
)

http_archive(
    name = "google_bazel_common",
    strip_prefix = "bazel-common-master",
    url = "https://github.com/google/bazel-common/archive/master.zip",
)
