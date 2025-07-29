import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aula.reducer';

export const AulaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aulaEntity = useAppSelector(state => state.aula.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aulaDetailsHeading">
          <Translate contentKey="edutechApp.aula.detail.title">Aula</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{aulaEntity.id}</dd>
          <dt>
            <span id="data">
              <Translate contentKey="edutechApp.aula.data">Data</Translate>
            </span>
          </dt>
          <dd>{aulaEntity.data ? <TextFormat value={aulaEntity.data} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="titulo">
              <Translate contentKey="edutechApp.aula.titulo">Titulo</Translate>
            </span>
          </dt>
          <dd>{aulaEntity.titulo}</dd>
          <dt>
            <span id="conteudo">
              <Translate contentKey="edutechApp.aula.conteudo">Conteudo</Translate>
            </span>
          </dt>
          <dd>{aulaEntity.conteudo}</dd>
          <dt>
            <Translate contentKey="edutechApp.aula.curso">Curso</Translate>
          </dt>
          <dd>{aulaEntity.curso ? aulaEntity.curso.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aula" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aula/${aulaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AulaDetail;
