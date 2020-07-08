package com.laamella.sexpression.model;

import com.laamella.sexpression.CharSink;
import com.laamella.sexpression.SExpressionsToTreeParser;
import com.laamella.sexpression.SExpressionsToTreeParser.Callback.DocumentGrabbingCallback;
import com.laamella.sexpression.SExpressionsStreamingLexer;
import com.laamella.sexpression.SExpressionsStreamingParser;
import com.laamella.sexpression.visitor.Visitor;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.nio.charset.StandardCharsets.*;

public class Document extends AtomList {
    public Document(List<SExpression> nodes, List<String> comments) {
        super(null, nodes, comments);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public <A, R> R visit(Visitor<A, R> visitor, A arg) throws Exception {
        if (visitor.enter(this, arg) == Visitor.EnterDecision.ENTER) {
            R r = visitor.accept(this, arg);
            visitor.exit(this, r, arg);
            return r;
        }
        return null;
    }

    @Override
    public boolean isDocument() {
        return true;
    }

    @Override
    public Document asDocument() {
        return this;
    }

    public static Document from(Reader reader) throws IOException {
        DocumentGrabbingCallback callback = new DocumentGrabbingCallback();
        final SExpressionsStreamingLexer parser =
                new SExpressionsStreamingLexer(
                        new SExpressionsStreamingParser(
                                new SExpressionsToTreeParser(callback)));
        parser.push(reader);
        return callback.document;
    }

    public static Document fromResource(String resourceName) throws IOException {
        DocumentGrabbingCallback callback = new DocumentGrabbingCallback();
        final SExpressionsStreamingLexer parser =
                new SExpressionsStreamingLexer(
                        new SExpressionsStreamingParser(
                                new SExpressionsToTreeParser(callback)));
        parser.pushResource(resourceName, UTF_8);
        return callback.document;
    }

    public static Document fromString(String string) {
        DocumentGrabbingCallback callback = new DocumentGrabbingCallback();
        final SExpressionsStreamingLexer parser =
                new SExpressionsStreamingLexer(
                        new SExpressionsStreamingParser(
                                new SExpressionsToTreeParser(callback)));
        parser.pushString(string);
        return callback.document;
    }
}
