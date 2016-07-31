package com.laamella.sexpression.model;

import com.laamella.sexpression.visitor.Visitor;
import javaslang.collection.Vector;

public class AtomList extends SExpression {
    private Vector<Node> nodes;
    private Vector<SExpression> list;

    public AtomList(Node... nodes) {
        setNodes(nodes);
    }

    public AtomList(Vector<Node> nodes) {
        setNodes(nodes);
    }

    public void add(Node node) {
        setNodes(nodes.append(node));
    }

    public void setNodes(Node... nodes) {
        if (nodes == null) {
            setNodes(Vector.empty());
        } else {
            setNodes(Vector.of(nodes));
        }
    }

    public void setNodes(Vector<Node> nodes) {
        nodes.forEach(n -> n.setParent(this));
        this.nodes = nodes;
        list = nodes.filter(Node::isSExpression).map(Node::asSExpression);
    }

    public void add(CharSequence atom) {
        setNodes(nodes.append(new Atom(atom)));
    }

    /**
     * @return all the nodes here, including comments, whitespace, etc.
     */
    public Vector<Node> nodes() {
        return nodes;
    }

    /**
     * @return the atoms and lists inside this list.
     */
    public Vector<SExpression> list() {
        return list;
    }


    @Override
    public <A, R> R visit(Visitor<A, R> visitor, A arg) throws Exception {
        return visitor.accept(this, arg);
    }

    @Override
    public boolean isList() {
        return true;
    }

    @Override
    public AtomList asList() {
        return this;
    }

    public boolean isAllAtoms() {
        for (Node e : nodes) {
            if (e.isList()) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
