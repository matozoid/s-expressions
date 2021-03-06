package com.laamella.sexpression.model;

import com.laamella.sexpression.CharSource;
import com.laamella.sexpression.codec.AtomCodec;
import com.laamella.sexpression.visitor.Visitor;
import io.vavr.collection.Vector;

public class Atom extends SExpression {
    public byte[] data;
    public AtomCodec codec;

    public Atom(SExpression parent, byte[] data, AtomCodec codec, Vector<String> comments) {
        super(parent, comments);
        this.data = data;
        this.codec = codec;
    }

    /**
     * @return the atom as it would appear in an s-expression.
     */
    public String encoded() {
        return codec.encode(data).get();
    }

    /**
     * @return the actual text represented by this atom.
     * If the atom contains binary data this is undefined, use raw() instead.
     */
    public String value() {
        return new String(data, CharSource.UTF8);
    }

    @Override
    public <A, R> R visit(Visitor<A, R> visitor, A arg) throws Exception {
        return visitor.accept(this, arg);
    }

    @Override
    public boolean isAtom() {
        return true;
    }

    @Override
    public Atom asAtom() {
        return this;
    }
}
