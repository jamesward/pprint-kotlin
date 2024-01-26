package io.exoquery.pprint

import kotlinx.serialization.KSerializer

class PPrinter<T>(val serializer: KSerializer<T>, override val config: PPrinterConfig = PPrinterConfig()): PPrinterBase<T>(config) {
  override fun treeify(x: T, escapeUnicode: Boolean, showFieldNames: Boolean): Tree {
    val encoder = TreeElementEncoder(escapeUnicode, showFieldNames)
    serializer.serialize(encoder, x)
    val tree = encoder.retrieve()
    return tree
  }
}
