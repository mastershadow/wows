package it.roccatello.wows.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

public class ImplicitNamingStrategy extends SpringImplicitNamingStrategy {
  private static final long serialVersionUID = -6482260783201224390L;

  @Override
  public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
      final String name;

      if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION || source.getAttributePath() == null) {
          name = transformEntityName(source.getEntityNaming());
      } else {
          name = transformAttributePath(source.getAttributePath());
      }

      return toIdentifier(name, source.getBuildingContext());
  }
}

