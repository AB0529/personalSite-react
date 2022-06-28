import { Formik, FormikFormProps, FormikHelpers, FormikProps, FormikValues } from "formik"
import { JSXElementConstructor, ReactElement, ReactFragment, ReactNode, ReactPortal } from "react";
import { AnySchema, object } from "yup"

export type TFormikFormSubmit = (values: FormikValues, formikHelpers: FormikHelpers<FormikValues>) => void | Promise<any>;
export interface IFormikFormField {
	name: string,
	initalValue: number | string,
	schema: AnySchema
}
export interface IFormikFormProps {
	fields: IFormikFormField[],
	onSubmit: TFormikFormSubmit,
	render?: any
}

export const FormikForm = (props: IFormikFormProps) => {
	let formInitalValues = {} as { [key: string]: string | number };
	let formValidationSchema = {};

	props.fields.forEach(field => {
		let validation = {} as { [key: string]: AnySchema };
		validation[field.name] = field.schema;

		formInitalValues[field.name] = field.initalValue;
		formValidationSchema = object().shape(validation);
	});

	return (
		<Formik
			initialValues={formInitalValues}
			validationSchema={formValidationSchema}
			onSubmit={props.onSubmit}

		>
			{props.render}
		</Formik>
	)
}