package edu.kit.informatik.logic;

/**
 * The type an assembly can be.
 *
 * @author Linus Schilpp
 * @version 1.0
 */
public enum AssemblyType {
    /**
     * the type BOM (Bill of Materials) represents an assembly that consists of different parts.
     */
    BOM,
    /**
     * the type COMPONENT represents an assembly that is a part itself and does not consist of other parts.
     */
    COMPONENT
}
