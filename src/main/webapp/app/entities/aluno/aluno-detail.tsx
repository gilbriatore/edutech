import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aluno.reducer';

export const AlunoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const alunoEntity = useAppSelector(state => state.aluno.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alunoDetailsHeading">
          <Translate contentKey="edutechApp.aluno.detail.title">Aluno</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{alunoEntity.id}</dd>
          <dt>
            <span id="nome">
              <Translate contentKey="edutechApp.aluno.nome">Nome</Translate>
            </span>
          </dt>
          <dd>{alunoEntity.nome}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="edutechApp.aluno.email">Email</Translate>
            </span>
          </dt>
          <dd>{alunoEntity.email}</dd>
          <dt>
            <span id="matricula">
              <Translate contentKey="edutechApp.aluno.matricula">Matricula</Translate>
            </span>
          </dt>
          <dd>{alunoEntity.matricula}</dd>
          <dt>
            <span id="dataNascimento">
              <Translate contentKey="edutechApp.aluno.dataNascimento">Data Nascimento</Translate>
            </span>
          </dt>
          <dd>
            {alunoEntity.dataNascimento ? (
              <TextFormat value={alunoEntity.dataNascimento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/aluno" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aluno/${alunoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AlunoDetail;
