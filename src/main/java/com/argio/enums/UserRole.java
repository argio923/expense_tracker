package com.argio.enums;

/**
 * Represents the role of a user in the system.
 * This enum defines two distinct roles:
 * - USER: A regular user with standard access privileges.
 * - ADMIN: A user with administrative privileges, capable of managing other users and system settings.
 *
 * Usage:
 * UserRole is typically used for role-based access control (RBAC) to determine user permissions
 * and access levels within the application.
 */
public enum UserRole {
    USER,
    ADMIN;

    public String name;

    UserRole() {}
}
