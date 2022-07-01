package com.ab0529.absiteold.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class File {
	@Id
	@org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
	private UUID id;

	@NonNull
	@Lob
	private byte[] content;
	@NonNull
	private String name;
	@NonNull
	private String contentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@NonNull
	private User user;
}
