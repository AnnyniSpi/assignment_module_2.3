package dev.annyni.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * todo Document type Writer
 */
@ToString(exclude = {"posts"})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "writer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts;

    @Enumerated(EnumType.STRING)
    private Status status;

}
