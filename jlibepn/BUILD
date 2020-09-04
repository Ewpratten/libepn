load("@rules_java//java:defs.bzl", "java_library", "java_test")
load("@google_bazel_common//tools/javadoc:javadoc.bzl", "javadoc_library")

package(default_visibility = ["//visibility:public"])

filegroup(
    name = "jlibepn",
    srcs = [
        ":epn",
        ":libepn-javadoc",
    ],
)

java_library(
    name = "epn",
    srcs = glob(["main/**/*.java"]),
    deps = [
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)

javadoc_library(
    name = "libepn-javadoc",
    srcs = glob(["main/**/*.java"]),
    doctitle = "LibEPN API",
    external_javadoc_links = [
        "http://docs.spring.io/spring/docs/4.3.x/javadoc-api/",
        "http://docs.oracle.com/javase/8/docs/api/",
        "http://docs.spring.io/spring-ws/docs/2.3.0.RELEASE/api/",
        "http://docs.spring.io/spring-security/site/docs/4.0.4.RELEASE/apidocs/",
        "https://commons.apache.org/proper/commons-math/javadocs/api-3.4/",
        "http://ejml.org/javadoc/",
    ],
    deps = [
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)

test_suite(
    name = "tests",
    tests = [
        ":direction_util_test",
        ":quaternion_util_test",
    ],
)

java_test(
    name = "direction_util_test",
    srcs = ["test/java/ca/retrylife/libepn/util/DirectionUtilTest.java"],
    test_class = "ca.retrylife.libepn.util.DirectionUtilTest",
    deps = [
        ":epn",
        "@maven//:junit_junit",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)

java_test(
    name = "quaternion_util_test",
    srcs = ["test/java/ca/retrylife/libepn/util/QuaternionUtilTest.java"],
    test_class = "ca.retrylife.libepn.util.QuaternionUtilTest",
    deps = [
        ":epn",
        "@maven//:junit_junit",
        "@maven//:org_apache_commons_commons_math3",
        "@maven//:org_ejml_ejml_all",
        "@maven//:org_ejml_ejml_simple",
    ],
)
