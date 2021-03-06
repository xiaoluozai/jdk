/*
 * Copyright (c) 2020, Red Hat, Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * @test id=c1
 * @build compiler.blackhole.BlackholeTarget
 *
 * @run main/othervm
 *      -Xmx1g
 *      -XX:TieredStopAtLevel=1
 *      -XX:+UnlockDiagnosticVMOptions -XX:+AbortVMOnCompilationFailure
 *      -XX:CompileCommand=blackhole,compiler/blackhole/BlackholeTarget.bh_*
 *      compiler.blackhole.BlackholeInstanceReturnTest
 */

/**
 * @test id=c2
 * @build compiler.blackhole.BlackholeTarget
 *
 * @run main/othervm
 *      -Xmx1g
 *      -XX:-TieredCompilation
 *      -XX:+UnlockDiagnosticVMOptions -XX:+AbortVMOnCompilationFailure
 *      -XX:CompileCommand=blackhole,compiler/blackhole/BlackholeTarget.bh_*
 *      compiler.blackhole.BlackholeInstanceReturnTest
 */

/**
 * @test id=c1-no-coops
 * @requires vm.bits == "64"
 * @build compiler.blackhole.BlackholeTarget
 *
 * @run main/othervm
 *      -Xmx1g -XX:-UseCompressedOops
 *      -XX:TieredStopAtLevel=1
 *      -XX:+UnlockDiagnosticVMOptions -XX:+AbortVMOnCompilationFailure
 *      -XX:CompileCommand=blackhole,compiler/blackhole/BlackholeTarget.bh_*
 *      compiler.blackhole.BlackholeInstanceReturnTest
 */

/**
 * @test id=c2-no-coops
 * @requires vm.bits == "64"
 * @build compiler.blackhole.BlackholeTarget
 *
 * @run main/othervm
 *      -Xmx1g -XX:-UseCompressedOops
 *      -XX:-TieredCompilation
 *      -XX:+UnlockDiagnosticVMOptions -XX:+AbortVMOnCompilationFailure
 *      -XX:CompileCommand=blackhole,compiler/blackhole/BlackholeTarget.bh_*
 *      compiler.blackhole.BlackholeInstanceReturnTest
 */

package compiler.blackhole;

public class BlackholeInstanceReturnTest {

    public static void main(String[] args) {
        runTries(BlackholeInstanceReturnTest::test_boolean);
        runTries(BlackholeInstanceReturnTest::test_byte);
        runTries(BlackholeInstanceReturnTest::test_char);
        runTries(BlackholeInstanceReturnTest::test_short);
        runTries(BlackholeInstanceReturnTest::test_int);
        runTries(BlackholeInstanceReturnTest::test_float);
        runTries(BlackholeInstanceReturnTest::test_long);
        runTries(BlackholeInstanceReturnTest::test_double);
        runTries(BlackholeInstanceReturnTest::test_Object);
    }

    private static final int CYCLES = 1_000_000;
    private static final int TRIES = 10;

    public static void runTries(Runnable r) {
        for (int t = 0; t < TRIES; t++) {
            BlackholeTarget.clear();
            r.run();
            BlackholeTarget.shouldBeEntered();
        }
    }

    private static void test_boolean() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_boolean((c & 0x1) == 0) != false) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_byte() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_byte((byte)c) != 0) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_char() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_char((char)c) != 0) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_short() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_short((short)c) != 0) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_int() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_int(c) != 0) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_float() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_float(c) != 0F) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_long() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_long(c) != 0L) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_double() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            if (t.bh_ir_double(c) != 0D) {
                throw new AssertionError("Return value error");
            }
        }
    }

    private static void test_Object() {
        BlackholeTarget t = new BlackholeTarget();
        for (int c = 0; c < CYCLES; c++) {
            Object o = new Object();
            if (t.bh_ir_Object(o) != null) {
                throw new AssertionError("Return value error");
            }
        }
    }

}
