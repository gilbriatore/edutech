import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inscricao.reducer';

export const InscricaoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inscricaoEntity = useAppSelector(state => state.inscricao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inscricaoDetailsHeading">
          <Translate contentKey="edutechApp.inscricao.detail.title">Inscricao</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{inscricaoEntity.id}</dd>
          <dt>
            <span id="dataInscricao">
              <Translate contentKey="edutechApp.inscricao.dataInscricao">Data Inscricao</Translate>
            </span>
          </dt>
          <dd>
            {inscricaoEntity.dataInscricao ? (
              <TextFormat value={inscricaoEntity.dataInscricao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="edutechApp.inscricao.aluno">Aluno</Translate>
          </dt>
          <dd>{inscricaoEntity.aluno ? inscricaoEntity.aluno.id : ''}</dd>
          <dt>
            <Translate contentKey="edutechApp.inscricao.curso">Curso</Translate>
          </dt>
          <dd>{inscricaoEntity.curso ? inscricaoEntity.curso.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/inscricao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inscricao/${inscricaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InscricaoDetail;
