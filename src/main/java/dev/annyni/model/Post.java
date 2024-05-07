package dev.annyni.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * todo Document type Post
 */
@ToString(exclude = {"labels", "writer"})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Column(updatable = false)
    private Date created;
    private Date updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private Writer writer;

    @ManyToMany
    @JoinTable(
        name = "post_label",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void addLabels(List<Label> newLabels){
        labels.addAll(newLabels);
    }
}
