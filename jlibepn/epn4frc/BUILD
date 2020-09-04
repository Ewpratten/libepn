load("@rules_java//java:defs.bzl", "java_library")
load("@google_bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "epn4frc",
    srcs = [
        ":epn_for_frc",
        ":libepn_for_frc-javadoc",
    ],
)

java_library(
    name = "epn_for_frc",
    srcs = glob(["main/**/*.java"]),
    deps = [
        "//jlibepn:epn",
        "@maven//:edu_wpi_first_wpilibj_wpilibj_java",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)

javadoc_library(
    name = "libepn_for_frc-javadoc",
    srcs = glob(["main/**/*.java"]),
    doctitle = "LibEPN4FRC API",
    external_javadoc_links = [
        "http://docs.spring.io/spring/docs/4.3.x/javadoc-api/",
        "http://docs.oracle.com/javase/8/docs/api/",
        "http://docs.spring.io/spring-ws/docs/2.3.0.RELEASE/api/",
        "http://docs.spring.io/spring-security/site/docs/4.0.4.RELEASE/apidocs/",
        "https://commons.apache.org/proper/commons-math/javadocs/api-3.4/",
        "http://ejml.org/javadoc/",
    ],
    deps = [
        "//jlibepn:epn",
        "@maven//:edu_wpi_first_wpilibj_wpilibj_java",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)
