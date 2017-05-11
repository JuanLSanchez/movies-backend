package es.juanlsanchez.movies.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
@ToString(exclude = "times")
public class Movie extends BaseEntity {

  @Column(name = "code", length = 100)
  private String code;
  @Column(name = "tit", length = 100)
  private String tit;
  @Column(name = "href", length = 255)
  private String href;

  // Data
  @Column(name = "title", length = 100)
  private String title;
  @Column(name = "description", columnDefinition = "text", length = 65535)
  private String description;
  @Column(name = "src_img_poster", length = 255)
  private String srcImgPoster;
  @Column(name = "src_img_large", length = 255)
  private String srcImgLarge;

  // Relationships
  @OneToMany(mappedBy = "movie")
  private List<Time> times;

}
