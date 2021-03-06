package com.laamella.sexpression.visitor;

import com.laamella.sexpression.model.*;
import io.vavr.collection.Seq;

import static com.laamella.sexpression.visitor.Visitor.EnterDecision.ENTER;

/**
 * Prints everything including whitespace and comments.
 */
public class LiteralPrinterVisitor implements Visitor<Appendable, Void> {
    @Override
    public Void accept(Atom atom, Appendable output) throws Exception {
        output.append(atom.encoded());
        return null;
    }

    @Override
    public Void accept(AtomList atomList, Appendable output) throws Exception {
        visit(atomList.asVector(), output);
        return null;
    }

    @Override
    public EnterDecision enter(AtomList atomList, Appendable output) throws Exception {
        output.append('(');
        return ENTER;
    }

    @Override
    public void exit(AtomList atomList, Void result, Appendable output) throws Exception {
        output.append(')');
    }

    @Override
    public EnterDecision enter(Document document, Appendable output) {
        return ENTER;
    }

    @Override
    public void exit(Document document, Void result, Appendable output) {

    }

    public void visit(Seq<Node> list, Appendable output) throws Exception {
        for (Node n : list) {
            n.visit(this, output);
        }
    }

    @Override
    public Void accept(Document document, Appendable output) throws Exception {
        visit(document.asVector(), output);
        return null;
    }

    @Override
    public Void accept(Comment comment, Appendable output) throws Exception {
        output.append(";").append(comment.text);
        return null;
    }

    @Override
    public Void accept(Whitespace whitespace, Appendable output) throws Exception {
        output.append(whitespace.whitespace);
        return null;
    }

    @Override
    public Void accept(LineTerminator lineTerminator, Appendable output) throws Exception {
        output.append("\n");
        return null;
    }
}
