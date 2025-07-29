import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAlunos } from 'app/entities/aluno/aluno.reducer';
import { getEntities as getCursos } from 'app/entities/curso/curso.reducer';
import { createEntity, getEntity, reset, updateEntity } from './inscricao.reducer';

export const InscricaoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const alunos = useAppSelector(state => state.aluno.entities);
  const cursos = useAppSelector(state => state.curso.entities);
  const inscricaoEntity = useAppSelector(state => state.inscricao.entity);
  const loading = useAppSelector(state => state.inscricao.loading);
  const updating = useAppSelector(state => state.inscricao.updating);
  const updateSuccess = useAppSelector(state => state.inscricao.updateSuccess);

  const handleClose = () => {
    navigate(`/inscricao${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAlunos({}));
    dispatch(getCursos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.dataInscricao = convertDateTimeToServer(values.dataInscricao);

    const entity = {
      ...inscricaoEntity,
      ...values,
      aluno: alunos.find(it => it.id.toString() === values.aluno?.toString()),
      curso: cursos.find(it => it.id.toString() === values.curso?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dataInscricao: displayDefaultDateTime(),
        }
      : {
          ...inscricaoEntity,
          dataInscricao: convertDateTimeFromServer(inscricaoEntity.dataInscricao),
          aluno: inscricaoEntity?.aluno?.id,
          curso: inscricaoEntity?.curso?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="edutechApp.inscricao.home.createOrEditLabel" data-cy="InscricaoCreateUpdateHeading">
            <Translate contentKey="edutechApp.inscricao.home.createOrEditLabel">Create or edit a Inscricao</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="inscricao-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('edutechApp.inscricao.dataInscricao')}
                id="inscricao-dataInscricao"
                name="dataInscricao"
                data-cy="dataInscricao"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="inscricao-aluno"
                name="aluno"
                data-cy="aluno"
                label={translate('edutechApp.inscricao.aluno')}
                type="select"
              >
                <option value="" key="0" />
                {alunos
                  ? alunos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="inscricao-curso"
                name="curso"
                data-cy="curso"
                label={translate('edutechApp.inscricao.curso')}
                type="select"
              >
                <option value="" key="0" />
                {cursos
                  ? cursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inscricao" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default InscricaoUpdate;
