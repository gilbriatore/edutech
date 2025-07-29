import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/aluno">
        <Translate contentKey="global.menu.entities.aluno" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/professor">
        <Translate contentKey="global.menu.entities.professor" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/curso">
        <Translate contentKey="global.menu.entities.curso" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/aula">
        <Translate contentKey="global.menu.entities.aula" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/inscricao">
        <Translate contentKey="global.menu.entities.inscricao" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
