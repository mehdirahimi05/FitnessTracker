package de.fherfurt.FitnessTrackerSystem.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * JPA entity representing a user account in the FitnessTrackerSystem.
 *
 * <p>Mapped to the {@code users} table. Each user holds login credentials,
 * a {@link UserRole} for access control, and a one-to-one relationship to
 * {@link UserProfile} for extended profile data.</p>
 *
 * <p>The no-args constructor is {@code PROTECTED} to prevent direct
 * instantiation outside of JPA and the same package — use the all-args
 * constructor or a dedicated factory/builder instead.</p>
 *
 * <p><b>Example:</b></p>
 * <pre>{@code
 * User user = new User(0, "mehdi", hashedPassword, UserRole.MEMBER, details);
 * }</pre>
 *
 * @author Mehdi Rahimi
 * @see UserRole
 * @see UserProfile
 */
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "userId")
@Entity
@Table(name = "users")
public class User {

    /**
     * Auto-generated primary key, managed by the database via identity strategy.
     * Do not set this manually — the value is assigned on first persist.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    /**
     * The unique login name of this user.
     * Cannot be {@code null} and must be unique across the {@code users} table.
     */
    @Column(unique = true, nullable = false)
    private String userName;

    /**
     * The user's hashed password.
     *
     * <p><b>Important:</b> Never store or assign a plain-text password here.
     * Always hash the value before passing it to this field (e.g. via BCrypt).</p>
     */
    private String passWord;

    /**
     * The role assigned to this user, persisted as its enum name (e.g. {@code "ADMIN"}).
     * Determines the user's permissions and access level throughout the system.
     *
     * @see UserRole
     */
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * Extended profile information belonging exclusively to this user.
     *
     * <p>The relationship is owned by this entity. The following cascade operations
     * are applied to the associated {@link UserProfile}:</p>
     * <ul>
     *   <li>{@code PERSIST} — details are saved when this user is first persisted.</li>
     *   <li>{@code MERGE}   — details are updated when this user is merged.</li>
     *   <li>{@code REMOVE}  — details are deleted when this user is deleted.</li>
     * </ul>
     *
     * <p>May be {@code null} if no details have been assigned yet.</p>
     *
     * @see UserProfile
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "user_details_id")
    private UserProfile userProfile;


}