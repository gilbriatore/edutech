import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCursos } from 'app/entities/curso/curso.reducer';
import { createEntity, getEntity, reset, updateEntity } from './aula.reducer';

export const AulaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cursos = useAppSelector(state => state.curso.entities);
  const aulaEntity = useAppSelector(state => state.aula.entity);
  const loading = useAppSelector(state => state.aula.loading);
  const updating = useAppSelector(state => state.aula.updating);
  const updateSuccess = useAppSelector(state => state.aula.updateSuccess);

  const handleClose = () => {
    navigate(`/aula${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    values.data = convertDateTimeToServer(values.data);

    const entity = {
      ...aulaEntity,
      ...values,
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
          data: displayDefaultDateTime(),
        }
      : {
          ...aulaEntity,
          data: convertDateTimeFromServer(aulaEntity.data),
          curso: aulaEntity?.curso?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="edutechApp.aula.home.createOrEditLabel" data-cy="AulaCreateUpdateHeading">
            <Translate contentKey="edutechApp.aula.home.createOrEditLabel">Create or edit a Aula</Translate>
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
                  id="aula-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('edutechApp.aula.data')}
                id="aula-data"
                name="data"
                data-cy="data"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('edutechApp.aula.titulo')}
                id="aula-titulo"
                name="titulo"
                data-cy="titulo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('edutechApp.aula.conteudo')}
                id="aula-conteudo"
                name="conteudo"
                data-cy="conteudo"
                type="textarea"
              />
              <ValidatedField id="aula-curso" name="curso" data-cy="curso" label={translate('edutechApp.aula.curso')} type="select">
                <option value="" key="0" />
                {cursos
                  ? cursos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aula" replace color="info">
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

export default AulaUpdate;
