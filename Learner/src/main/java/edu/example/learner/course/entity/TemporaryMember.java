package edu.example.learner.course.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;


@Embeddable
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "temporaryMember")
public class TemporaryMember {
    //임시 멤버
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;


    @OneToMany(mappedBy = "temporaryMember", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alarm> alarms = new ArrayList<>();

}
