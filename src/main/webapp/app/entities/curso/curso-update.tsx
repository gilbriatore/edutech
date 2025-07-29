import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProfessors } from 'app/entities/professor/professor.reducer';
import { createEntity, getEntity, reset, updateEntity } from './curso.reducer';

export const CursoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const professors = useAppSelector(state => state.professor.entities);
  const cursoEntity = useAppSelector(state => state.curso.entity);
  const loading = useAppSelector(state => state.curso.loading);
  const updating = useAppSelector(state => state.curso.updating);
  const updateSuccess = useAppSelector(state => state.curso.updateSuccess);

  const handleClose = () => {
    navigate(`/curso${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfessors({}));
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
    if (values.cargaHoraria !== undefined && typeof values.cargaHoraria !== 'number') {
      values.cargaHoraria = Number(values.cargaHoraria);
    }

    const entity = {
      ...cursoEntity,
      ...values,
      professor: professors.find(it => it.id.toString() === values.professor?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cursoEntity,
          professor: cursoEntity?.professor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="edutechApp.curso.home.createOrEditLabel" data-cy="CursoCreateUpdateHeading">
            <Translate contentKey="edutechApp.curso.home.createOrEditLabel">Create or edit a Curso</Translate>
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
                  id="curso-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('edutechApp.curso.nome')}
                id="curso-nome"
                name="nome"
                data-cy="nome"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('edutechApp.curso.descricao')}
                id="curso-descricao"
                name="descricao"
                data-cy="descricao"
                type="text"
              />
              <ValidatedField
                label={translate('edutechApp.curso.cargaHoraria')}
                id="curso-cargaHoraria"
                name="cargaHoraria"
                data-cy="cargaHoraria"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="curso-professor"
                name="professor"
                data-cy="professor"
                label={translate('edutechApp.curso.professor')}
                type="select"
              >
                <option value="" key="0" />
                {professors
                  ? professors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/curso" replace color="info">
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

export default CursoUpdate;
