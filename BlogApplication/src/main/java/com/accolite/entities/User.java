package com.accolite.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name="user_name",nullable=false,length = 100)
	private String name;
	private String email;
	private String password;
	private String about;
	
	
	//cascade all -> all the parent transactions will be reflected to child
	//-if parent is deleted, child also will be deleted
	
	//fetch type-lazy- delays the initialization of the resource
	//eager- initializes or loads a resource as soon as the code is executed
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	List<Post> posts=new ArrayList<>();
	
	
	//one user can have many roles AND one role can have many users -> many to many
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="user_role",
				joinColumns = @JoinColumn(name="user", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id")
			)
	Set<Role> roles=new HashSet<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = this.roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}


	@Override
	public String getUsername() {
		System.out.println("inside rolde- getUsername"+this.email);
		return this.email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}
}
