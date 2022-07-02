package com.ab0529.absite.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@DynamicUpdate
@Transactional
public class Ban {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
}
