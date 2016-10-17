package net.println.kt05

/**
 * a_b_c d_e f_g_h_j
 * a b c d e f g h j
 * Created by benny on 10/14/16.
 */
fun main(vararg args: String) {
    args.flatMap {
        it.split("_")
    }.map {
        print("$it  ${it.length}")
    }
}