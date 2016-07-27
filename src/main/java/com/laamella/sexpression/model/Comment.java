package com.laamella.sexpression.model;

import com.laamella.sexpression.visitor.Visitor;

import java.util.function.Consumer;

public class Comment extends Meta {
	public final String text;

	public Comment(String text) {
		this.text = text;
	}

	@Override
	public <A, R> R visit(Visitor<A, R> visitor, A arg) throws Exception {
		return visitor.accept(this, arg);
	}

	@Override
	public Otherwise whenComment(Consumer<Comment> action) {
		action.accept(this);
		return new Otherwise(false);
	}

	@Override
	public Otherwise whenWhitespace(Consumer<Whitespace> action) {
		return new Otherwise(true);
	}

	@Override
	public Otherwise whenLineTerminator(Consumer<LineTerminator> action) {
		return new Otherwise(true);
	}

	@Override
	public boolean isComment() {
		return true;
	}

	@Override
	public boolean isWhitespace() {
		return false;
	}

	@Override
	public boolean isLineTerminator() {
		return false;
	}

	@Override
	public Comment asComment() {
		return this;
	}

	@Override
	public LineTerminator asLineTerminator() {
		throw new IllegalStateException();
	}

	@Override
	public Whitespace asWhitespace() {
		throw new IllegalStateException();
	}

}
